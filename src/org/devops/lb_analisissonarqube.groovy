package org.devops

def testCoverage() {
    // Ejecuta las pruebas con npm y genera cobertura
    sh 'npm test -- --coverage'
}

def analisisSonar(gitName) {
    // Define la variable scannerHome para la ruta del sonar-scanner
    def scannerHome = tool 'sonar-scanner'

    // Verifica si sonar-scanner está disponible
    if (scannerHome) { 
        // Ejecuta el análisis con el sonar-scanner
        withSonarQubeEnv("sonar-scanner") {
            sh """
                ${scannerHome}/bin/sonar-scanner \
                -Dsonar.projectKey=${gitName} \
                -Dsonar.projectName=${gitName} \
                -Dsonar.sources=src \
                -Dsonar.tests=src/_test_ \
                -Dsonar.exclusions=**/*.test.js \
                -Dsonar.testExecutionReportPaths=./test-report.xml \
                -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info
            """
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}
