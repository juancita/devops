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
        stages {
            stage('Extract Project Name') {
                steps { 
                    script {
                        def urlGitHub = sh(script: 'git config --get remote.origin.url', returnStdout: true).trim()
                        echo "URL del repositorio Git: ${urlGitHub}" 
                        def projectGitName = urlGitHub.replaceAll(/^.*\/([^/]+)\.git$/, '$1') 
                        echo "Nombre del proyecto extraído: ${projectGitName}" 
                        env.projectGitName = projectGitName
                    } 
                } 
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        org.devops.lb_buildimagen.buildImageDocker(env.projectGitName)
                    }
                }
            }
            stage('Publish Docker Image') {
                steps {
                    script {
                        org.devops.lb_publicardockerhub.publicarImagen(env.projectGitName)
                    }
                }
            }
            stage('Deploy Docker Container') {
                steps {
                    script {
                        org.devops.lb_deploydocker.despliegueContenedor(env.projectGitName)
                    }
                }
            }
            stage('OWASP Security Analysis') {
                steps {
                    script {
                        org.devops.lb_owasp.AnalisisOwasp(env.projectGitName)
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
