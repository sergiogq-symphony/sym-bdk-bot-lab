# Sym BDK Python bot lab
Creating bots using the Symphony BDK in Python

### Set up
```shell
# To install it
npm i -g yo generator-symphony
or
npm i -g yo @finos/generator-symphony

# Check if it is installed correctly
yo doctor
```

### Create a new project with Java
```shell
mkdir orders-bot && cd orders-bot/
yo symphony 2.0

# Run the app
./gradlew run
```

### Create a new project with Python
```shell
mkdir orders-bot && cd orders-bot/
yo symphony 2.0
or
yo @finos/symphony 2.0
  -> develop2.symphony.com
  -> sergiogq-orders-bot
  -> Bot Application
  -> Python
  -> Create the sercive account in the Pod and add the generate public key

python3 -m venv env
source env/bin/activate
pip3 install -r requirements.txt

# Run the bot app
python3 -m src
```

