package org.devops

// Clona el repositorio desde GitHub en la rama especificada
def clone() {
    // Se asegura de que las variables del entorno estén bien pasadas en el comando
    git branch: "${env.nameBranch}", url: "${env.UrlGitHub}"
}
