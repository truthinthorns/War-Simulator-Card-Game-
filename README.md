This "simulator" figures out who should win a game of War, either based on a real deal, or a random one. If it's a real one, the user enters the cards each player has, and it will run through the game and decide who would win. If it's a random one, it does the same thing, except the user doesn't have to enter any cards.

To run this, you can either run the Java file directly, or if you have Docker installed, you can run
docker build -t warsim .
docker run -it --rm --name war warsim
