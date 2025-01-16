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
        // Verifica si los archivos requeridos existen
        if (!fileExists('test-report.xml')) {
            error 'El archivo test-report.xml no existe. Asegúrate de que las pruebas generen este archivo.'
        }

        if (!fileExists('coverage/lcov.info')) {
            error 'El archivo coverage/lcov.info no existe. Asegúrate de generar cobertura con npm test.'
        }

        // Ejecuta el análisis con el sonar-scanner
        withSonarQubeEnv("sonar-scanner") {
            sh """
                ${scannerHome}/bin/sonar-scanner \
                -Dsonar.projectKey=${gitName} \
                -Dsonar.projectName=${gitName} \
                -Dsonar.sources=src \
                -Dsonar.tests=tests \
                -Dsonar.exclusions=**/*.test.js \
                -Dsonar.testExecutionReportPaths=./test-report.xml \
                -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info
            """
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}
