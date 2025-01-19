def call() {
    pipeline {
        agent any

        tools {
            nodejs 'NodeJS' // Asegúrate de configurar NodeJS en Jenkins
        }

        environment {
            nameBranch = "main"
            UrlGitHub = "https://github.com/juancita/RetoJenkinsFuncional"
        }

        stages {
            stage('Clonar y Construir') {
                steps {
                    script {
                        org.devops.lb_buildartefacto.clone()

                    }
                }
            }

            stage('Pruebas y Análisis SonarQube') {
                steps {
                    script {
                        org.devops.lb_analisissonarqube.testCoverage()
                        org.devops.lb_analisissonarqube.analisisSonar(env.nameBranch)
                    }
                }
            }
        }

        post {
            always {
                echo 'Pipeline ejecutado con éxito.'
            }
            failure {
                echo 'Hubo un fallo en la ejecución del pipeline.'
            }
        }
    }
}
