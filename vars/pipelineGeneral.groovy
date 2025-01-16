import org.devops.lb_analisissonarqube
import org.devops.lb_buildartefacto

def call(Map config) {
    def lb_buildartefacto = new lb_buildartefacto()
    def lb_analisissonarqube = new lb_analisissonarqube()
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
                        org.devops.lb_buildartefacto.clone()
                        org.devops.lb_buildartefacto.install()
                        org.devops.lb_buildartefacto.build()
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
