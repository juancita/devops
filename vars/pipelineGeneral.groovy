import org.devops.lb_analisissonarqube
import org.devops.lb_buildartefacto

def call(Map config = [:]) {
    def buildArtefacto = new lb_buildartefacto()
    def analisisSonar = new lb_analisissonarqube()

    pipeline {
        agent any

        tools {
            nodejs 'NodeJS' 
        }

        environment {
            nameBranch = "main"
            UrlGitHub = "https://github.com/juancita/RetoJenkinsFuncional"
        }

        stages {
            stage('Clonar y Construir') {
                steps {
                    script {
                        // Llamar a los métodos de la instancia buildArtefacto
                        buildArtefacto.clone()
                    }
                }
            }

            stage('Pruebas y Análisis SonarQube') {
                steps {
                    script {
                        // Llamar a los métodos de la instancia analisisSonar
                        analisisSonar.testCoverage()
                        analisisSonar.analisisSonar(env.nameBranch)
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
