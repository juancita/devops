def call(Map params = [:]) {
    pipeline {
        agent any
        tools {
            nodejs 'NodeJS'
        }
        environment {
            projectName = 'RetoJenkinsFuncional' // Nombre directo del proyecto
        }
        stages {
            stage('Build Docker Image') {
                steps {
                    script {
                        lb_buildimagen.buildImageDocker(env.projectName)
                    }
                }
            }
            stage('Publish Docker Image') {
                steps {
                    script {
                        lb_publicardockerhub.publicarImagen(env.projectName)
                    }
                }
            }
            stage('Deploy Docker Container') {
                steps {
                    script {
                        lb_deploydocker.despliegueContenedor(env.projectName)
                    }
                }
            }
            stage('OWASP Security Analysis') {
                steps {
                    script {
                        lb_owasp.AnalisisOwasp(env.projectName)
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
