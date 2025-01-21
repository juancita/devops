import org.devops.lb_buildimagen
import org.devops.lb_deploydocker
import org.devops.lb_owasp
import org.devops.lb_publicardockerhub

def call(Map config = [:]) {
    def lb_buildimagen = new lb_buildimagen()
    def lb_deploydocker = new lb_deploydocker()
    def lb_owasp = new lb_owasp()
    def lb_publicardockerhub = new lb_publicardockerhub()

    pipeline {
        agent any
        tools {
            nodejs 'NodeJS'
        }
        environment {
            projectName = "${params.UrlGitHub.tokenize('/')[-1].split('\\.')[0]}"
        }
        stages {
            stage('Build Docker Image') {
                steps {
                    script {
                        org.devops.lb_buildimagen.buildImageDocker(env.projectName)
                    }
                }
            }
            stage('Publish Docker Image') {
                steps {
                    script {
                        org.devops.lb_publicardockerhub.publicarImagen(env.projectName)
                    }
                }
            }
            stage('Deploy Docker Container') {
                steps {
                    script {
                        org.develop.lb_deploydocker.despliegueContenedor(env.projectName)
                    }
                }
            }
            stage('OWASP Security Analysis') {
                steps {
                    script {
                        org.develop.lb_owasp.AnalisisOwasp(env.projectName)
                    }
                }
            }
        }
        post {
            always {
                echo "Pipeline execution completed."
            }
            success {
                echo "Pipeline executed successfully."
            }
            failure {
                echo "Pipeline failed. Please check the logs."
            }
        }
    }
}
