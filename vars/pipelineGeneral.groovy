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
            projectGitName="https://github.com/juancita/RetoJenkinsFuncional"

        }
        stages {

            stage('Build Docker Image') {
                steps {
                    script {
                        lb_buildimagen.buildImageDocker(env.projectGitName)
                    }
                }
            }
            stage('Publish Docker Image') {
                steps {
                    script {
                        lb_publicardockerhub.publicarImagen(env.projectGitName)
                    }
                }
            }
            stage('Deploy Docker Container') {
                steps {
                    script {
                        lb_deploydocker.despliegueContenedor(env.projectGitName)
                    }
                }
            }
            stage('OWASP Security Analysis') {
                steps {
                    script {
                        lb_owasp.AnalisisOwasp(env.projectGitName)
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
