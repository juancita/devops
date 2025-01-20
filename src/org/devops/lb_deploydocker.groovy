package org.develop

def despliegueContenedor(projectGitName){
    sh "docker pull juancita/RetoJenkinsFuncional"
    sh """docker run -d --name ${projectGitName} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --usr root juancita/${projectGitName}
    """
}
