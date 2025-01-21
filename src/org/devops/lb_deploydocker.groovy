package org.devops

def despliegueContenedor(projectGitName){
    // Detener y eliminar cualquier contenedor previo si es necesario
    // sh "docker stop ${projectGitName.toLowerCase()}"
    // sh "docker rm ${projectGitName.toLowerCase()}"

    // Usar un nombre fijo para la imagen en el pull
    sh "docker pull juancita/retojenkinsfuncional"

    // Correr el contenedor usando el projectGitName como nombre, asegurando que sea minúscula
    sh """docker run -d --name ${projectGitName.toLowerCase()} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --user root juancita/retojenkinsfuncional
    """
}
