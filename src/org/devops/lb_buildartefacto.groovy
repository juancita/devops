package org.devops

// Clona el repositorio desde GitHub en la rama especificada
def clone() {
    // Se asegura de que las variables del entorno estén bien pasadas en el comando
    git branch: "${env.nameBranch}", url: "${env.UrlGitHub}"
}

// Instala las dependencias de npm
def install() {
    // Ejecuta el comando npm install para instalar las dependencias del proyecto
    sh 'npm install'
}

// Construye el proyecto
def build() {
    // Ejecuta el comando de construcción
    sh 'npm run build'
}
