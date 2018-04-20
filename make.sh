#!/bin/bash
VERSION=$(cat VERSION)
SERVER=164.132.199.58
TESTING_PORT=1727
STAGING_PORT=1728
PRODUCTION_PORT=1729
SERVER_KEY=server
CLIENT_KEY=client
LOCALCLIENT_KEY=localclient
STAGING_KEY=staging
TESTING_KEY=testing
PRODUCTION_KEY=production

function show_usage {
  echo ""
  echo -e "\tStarts a Subluminal server or client on a predifined port."
  echo -e "\tServers are started locally, while clients connect to a remote server."
  echo ""
  echo -e "\tUsage Information:"
  echo -e "\t./make.sh <OPTIONS1> <OPTIONS2>"
  echo ""
  echo -e "\tOPTIONS1:"
  echo -e "\thelp \t\tThis message"
  echo -e "\twatch \t\tStart gradle continuous build ("gradle build -x check -x test --continuous")."
  echo -e "\tclient \t\tStart the client GUI and connect to $SERVER."
  echo -e "\localclient \t\t Start the client GUI and connect to localhost."
  echo -e "\tserver \t\tStarts the server on localhost."
  echo ""
  echo -e "\tOPTIONS2:"
  echo -e "\tstaging \t Connect to staging environment (from dev branch, port $STAGING_PORT)."
  echo -e "\ttesting \t... testing ... \t(from release branch, port $TESTING_PORT)."
  echo -e "\tproduction \t... production ... \t(from master branch, port $PRODUCTION_PORT)."
  echo ""
  echo -e "\tHint: If the jar can't be started, check if you got the right version"
  echo -e "\tnumber in your VERSION file."
}

if [[ "$1" = "help" ]] || [[ $# -eq 0 ]];then
  show_usage
elif [[ "$1" = "watch" ]];then
  gradle build -x test -x check -x javadoc -x javadocjar -x sourcesjar --continuous
elif [[ "$1" = "testenv" ]];then
  echo "//TODO: Make client and server start"
else
  case $2 in
  $STAGING_KEY)
    case $1 in
    $CLIENT_KEY)
      echo "Starting $STAGING_KEY $CLIENT_KEY..."
      SWITCH=$CLIENT_KEY
      ADRESS=$SERVER:$STAGING_PORT
      ;;
    $LOCALCLIENT_KEY)
      echo "Starting $STAGING_KEY $LOCALCLIENT_KEY"
      SWITCH=$CLIENT_KEY
      ADRESS=localhost:$STAGING_PORT
      ;;
    $SERVER_KEY)
      echo "Start $STAGING_KEY $SERVER_KEY..."
      SWITCH=$SERVER_KEY
      ADRESS=$STAGING_PORT
      ;;
    *)
      echo "Unknown first argument!"
      exit 1
      ;;
    esac
    ;;
  $TESTING_KEY)
    case $1 in
    $CLIENT_KEY)
      echo "Starting $TESTING_KEY $CLIENT_KEY..."
      SWITCH=$CLIENT_KEY
      ADRESS=$SERVER:$TESTING_PORT
      ;;
    $LOCALCLIENT_KEY)
      echo "Starting $TESTING_KEY $LOCALCLIENT_KEY"
      SWITCH=$CLIENT_KEY
      ADRESS=localhost:$TESTING_PORT
      ;;
    $SERVER_KEY)
      echo "Start $TESTING_KEY $SERVER_KEY..."
      SWITCH=$SERVER_KEY
      ADRESS=$TESTING_PORT
      ;;
    *)
      echo "Unknown first argument!"
      exit 1
      ;;
    esac
    ;;
  $PRODUCTION_KEY)
    case $1 in
    $CLIENT_KEY)
      echo "Starting $PRODUCTION_KEY $CLIENT_KEY..."
      SWITCH=$CLIENT_KEY
      ADRESS=$SERVER:$PRODUCTION_PORT
      ;;
    $LOCALCLIENT_KEY)
      echo "Starting $PRODUCTION_KEY $LOCALCLIENT_KEY"
      SWITCH=$CLIENT_KEY
      ADRESS=localhost:$PRODUCTION_PORT
      ;;
    $SERVER_KEY)
      echo "Start $PRODUCTION_KEY $SERVER_KEY..."
      SWITCH=$SERVER_KEY
      ADRESS=$PRODUCTION_PORT
      ;;
    *)
      echo "Unknown first argument!"
      exit 1
      esac
      ;;
  *)
    echo "Unknown second argument!"
    exit 1
    ;;
  esac

  java -jar ./app/build/libs/Subluminal-$VERSION.jar $SWITCH $ADRESS
fi

exit 0
