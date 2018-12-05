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
```
mvn clean
mvn compile
mvn spring-boot:run
```

---

### To deploy to heroku
```
mvn clean
mvn compile
./setHerokuEnv.py --app ucsb-APPNAME
mvn package heroku:deploy

Note:test appname was ucsb-giveaway
  must adjust for official site listed below 

```
Website: https://cs56-f18-buy-sell-giveaway.herokuapp.com


### Notes for testing:

```
https://ucsb-giveaway.herokuapp.com/
is unofficial site test for oauth fully functional on its own

https://ucsb-giveaway.herokuapp.com/protected/new_post
will prompt you to login to github in order to make new post

https://ucsb-giveaway.herokuapp.com/admin/post
page to approve post, must be an admin to visit or error page 403 will occur
note: I added wilson to group https://github.com/ucsb-buy-sell-members as an admin, 
so he should be able to view page and approve/remove posts to go to homepage.
```
