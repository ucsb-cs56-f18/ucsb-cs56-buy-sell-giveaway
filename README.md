# ucsb-cs56-buy-sell-giveaway

# Fall 2018
Description: Users can exchange free items. The two users are givers and getters

- Zain - ghesebull
- Zach - zholoien
- Yinon - yinonrousso
- Rami - ramipinto
- Juan - juangonzalez20
- Derek - derektkbang

---

### To run locally

### Setting up your database
You need to create a new mlab database and configure. For this you can follow this tutorial: https://ucsb-cs56-pconrad.github.io/topics/mongodb_mlab/

You should pay special attention to Step 3: *Adding a Collection* and *Creating the user and password*. 

Once you have this. You need edit the *localhost.json* file with the correct information. 

```
{
 "spring.data.mongodb.database":"fill-in-database-name",
 "spring.data.mongodb.host":"fill-in-host",
 "spring.data.mongodb.password":"fill-in-password",
 "spring.data.mongodb.port":"fill-in-port",
 "spring.data.mongodb.username":"fill-in-username"
}
```

### Running the application

```
mvn clean
mvn compile
. env.sh
mvn spring-boot:run
```

---

### To deploy to heroku

Include your mlab database information in your heroku app config variables. (Setting>Reveal Config Var)

```
mvn clean
mvn compile
mvn package heroku:deploy
```

Website: https://cs56-f18-buy-sell-giveaway.herokuapp.com
