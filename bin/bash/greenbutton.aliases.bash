# Aliases for Greenbutton projects.
# To enable:
#   ln -s ~/workspace/OpenESPI-Greenbutton-java/bin/bash/greenbutton.aliases.bash ~/.bash_it/aliases/available
#   bash-it enable alias greenbutton

cite about-alias
about-alias 'general aliases'

alias gb="cd ~/workspace/OpenESPI-Greenbutton-java"
alias gbdc="cd ~/workspace/OpenESPI-Greenbutton-java/OpenESPI-DataCustodian-java"
alias gbtp="cd ~/workspace/OpenESPI-Greenbutton-java/OpenESPI-ThirdParty-java"
alias eachsub="git submodule foreach"

function fetch-all {
  gb
  git co master
  git pull --rebase
  git submodule foreach git co master
  git submodule foreach git pull --rebase
}
