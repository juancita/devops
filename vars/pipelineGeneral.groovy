// pipelineGeneral.groovy
def call() {
    // Definir el agente de ejecución para el pipeline
    pipeline {
        agent any

        // Herramientas necesarias
        tools {
            nodejs 'NodeJS' // Asegúrate de que NodeJS esté configurado en Jenkins
        }

        environment {
            // Definir las variables de entorno
            nameBranch = "main" // Puedes personalizar la rama aquí
            UrlGitHub = "https://github.com/juancita/to-do-list" // Cambia la URL según sea necesario
        }

        stages {
            stage('Clonar y Construir') {
                steps {
                    script {
                        // Invocar la librería para clonar y construir el proyecto
                        org.devops.lb_buildartefacto.cloneAndBuild()
                    }
                }
            }

            stage('Análisis SonarQube') {
                steps {
                    script {
                        // Invocar la librería para realizar el análisis en SonarQube
                        org.devops.lb_analisissonarqube.analyzeSonarQube()
                    }
                }
            }
        }

        post {
            always {
                // Limpieza o acciones que siempre se ejecutan después de las etapas
                echo 'Pipeline ejecutado con éxito.'
            }
            failure {
                // Acciones si el pipeline falla
                echo 'Hubo un fallo en la ejecución del pipeline.'
            }
        }
    }
}
