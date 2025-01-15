def call() {
    pipeline {
        agent any

        tools {
            nodejs 'NodeJS' // Asegúrate de configurar NodeJS en Jenkins
        }

        environment {
            nameBranch = "main"
            UrlGitHub = "https://github.com/juancita/to-do-list"
        }

        stages {
            stage('Clonar y Construir') {
                steps {
                    script {
                        lb_buildartefacto.clone()
                        lb_buildartefacto.install()
                        lb_buildartefacto.build()
                    }
                }
            }

            stage('Pruebas y Análisis SonarQube') {
                steps {
                    script {
                        lb_analisissonarqube.testCoverage()
                        lb_analisissonarqube.analisisSonar(env.nameBranch)
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
