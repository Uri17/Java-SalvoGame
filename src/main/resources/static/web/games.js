
var tableLeaderBoard = new Vue({
    el: "#tableLeaderBoard",
    data: {
      gameData:{},
      leaderBoard:{},
      date:"",
      ourData:{

      userName: "",
      email:"",
      pwd: "",


       },

      player:"",


      },


 methods: {

  fetchLogin: function () {

  console.log(this.userName);

 fetch("/app/login", {
                 credentials: 'include',
         headers: {
             'Content-Type': 'application/x-www-form-urlencoded'
         },
         method: 'POST',
         body: this.getBody(this.ourData)



     })
     .then(function (data) {
         console.log('Request success: ', data);
         window.location.reload()

          if(data.status !== 200){


            alert("Player userName does not exists, do a signUp first.");

               }


     })
     .catch(function (error) {
         console.log('Request failure: ', error);
     });

     },

fetchSignUp: function () {

fetch("/api/players", {
                                     credentials: 'include',
                             headers: {
                                 'Content-Type': 'application/x-www-form-urlencoded'
                             },
                             method: 'POST',
                             body: this.getBody(this.ourData)



                         })
                         .then(function (data) {
                             console.log('Request success: ', data);

                             if(data.status == 201){

                             tableLeaderBoard.fetchLogin();

                             }

                           else{

                           alert("This player userName already exists or need an email to signUp, try again.");

                           }


                         })
                         .catch(function (error) {
                             console.log('Request failure: ', error);
                         });

                         },





     GetForm: function(){  // No haria falta esta función si lo hago con v-model y el fetchLogin se podria hacer directamente

     //this.ourData.email = document.getElementById("email").value;
     //this.ourData.username = document.getElementById("userName").value;
     //this.ourData.pwd = document.getElementById("pwd").value;
     this.fetchLogin();

     },



     logout: function () {

     fetch("/app/logout", {

                          method: 'POST',
                     //     body: this.getBody(this.ourData)


                })
                     .then(function (data) {
                         console.log('Request success: ', data);
                         window.location.reload()
                     })
                     .catch(function (error) {
                         console.log('Request failure: ', error);
                     });

             },

 getBody: function (json) {
     var body = [];
     for (var key in json) {
         var encKey = encodeURIComponent(key);
         var encVal = encodeURIComponent(json[key]);
         body.push(encKey + "=" + encVal);
     }
     return body.join("&");
 },


  CreateGame: function () {

      fetch("/api/games", {
              credentials: 'include',
                                   headers: {
                                       'Content-Type': 'application/x-www-form-urlencoded'
                                   },

                           method: 'POST',
                           //body: this.getBody(this.ourData)


                 })
                      .then(function (data) {
                          console.log('Request success: ', data);
                          window.location.reload()

                      })
                      .catch(function (error) {
                          console.log('Request failure: ', error);
                      });

              },


 JoinGame: function (id) {


      fetch("/api/games/"+ id +"/players", {
              credentials: 'include',
                                   headers: {
                                       'Content-Type': 'application/x-www-form-urlencoded'
                                   },

                           method: 'POST',
                           //body: this.getBody(this.ourData)

                 })
                      .then(function (data) {
                          console.log('Request success: ', data);
                          return data.json()


                      })
                      .then(function(json){
                        console.log(json);
                        window.location = "/web/game.html?gp=" + json.gpid;


                      })
                      .catch(function (error) {
                          console.log('Request failure: ', error);
                      });

              },









// FETCH FOR DATA GAME AND LEADERBOARD...FORMAT DATE AND SORTED FUNCTIONS//

        fetchGames: function () {

            fetch('/api/games/').then(response => {

                if (response.ok) {
                    return response.json();
                }
                throw new Error(response.statusText);
            }).then(json => {

                this.gameData = json; //.games  Solo haría falta si no esta la ruta en el html.
                console.log(this.gameData);
                this.player= json.player


                //this.sortedPlayer= json.sortedPlayer
                //this.formatDate(dateString);



            }).catch(function (error) {
                console.log("Request failed:" + error.message);
            });

        },


 fetchLeaderBoard: function () {

            fetch('/api/leaderboard/').then(response => {

                if (response.ok) {
                    return response.json();
                }
                throw new Error(response.statusText);
            }).then(json => {

                this.leaderBoard = json;
                console.log(this.leaderBoard);




            }).catch(function (error) {
                console.log("Request failed:" + error.message);
            });

        },

/*fetchPlayers: function(){  NOT NEED, WE CAN TAKE JSON WITH GAME DATA

fetch("/api/players/").then(response => {

                          if (response.ok) {
                                         return response.json();
                                     }
                                     throw new Error(response.statusText);
                                 }).then(json => {

                                     this.playerData = json.player;

                              }).catch(function (error) {
                                             console.log("Request failed:" + error.message);
                                         });

                               },*/


                               /* gpid: function () {  Not need we did on vue

                                           let gameUrl = "/web/game.html?gp=" + gpid;

                                          },


                                           var gameUrl = "/web/game.html?gp=" + gameData.gpid;
                                           this.gp = url.searchParams.get("gp");
                                           this.fetchLogin();
                                       },*/


      formatDate: function (dateString) {
           var monthNames = [
   "January", "February", "March",
   "April", "May", "June", "July",
   "August", "September", "October",
   "November", "December"
 ];

var date = new Date(dateString);

 var day = date.getDate();
 var monthIndex = date.getMonth();
 var year = date.getFullYear();

 return day + ' ' + monthNames[monthIndex] + ' ' + year;
}



        },


/*sortedPlayer: function (gp) {

 return gp.sort(
               (a, b) => (Number(a.id) > Number(b.id)) ?
               1 :
               (
                   (Number(b.id) > Number(a.id)) ?
                   -1 :
                   0
               )
           );
       },*/



created:function(){


    this.fetchGames();
    this.fetchLeaderBoard();





        },

computed: {





    /*

    sortedPlayer: function () {
    return this.player.sort((a, b) => (b.id - a.id));

        }
    */




},




        });




























































/*function getItemHtml(ListGames) {
console.log(ListGames);

/*
var date = new Date(ListGames.created).toLocaleString() + " - ";
var ol = document.createElement("ol");
var li = document.createElement("li");
li.append(date);

if (ListGames.gamePlayers.length > 1) {

for (i = 0; i < ListGames.gamePlayers.length; i++) {

var player = ListGames.gamePlayers[i].player.email;


li.append(player);
ol.append(li);



}

}

else{
        var date = new Date(ListGames.created).toLocaleString() + " - ";
        var ol = document.createElement("ol");
        var li = document.createElement("li");
        li.append(date);


        var playerMsg = (ListGames.gamePlayers[0].player.email) + " vs " + "waiting for player";


        li.append(playerMsg);
        ol.append(li);


    }



return ol.innerHTML;*/

/*var date = new Date(ListGames.created).toLocaleString();

return "<li>" +
 date + " - " + ListGames.gamePlayers[0].player.email + "(you)" + " vs " + (ListGames.gamePlayers[1] ? ListGames.gamePlayers[1].player.email : "Wait for player") + "</li>";

}


function getListHtml(data) {
  return data.map(getItemHtml).join("");
}

function renderList(data) {
  var html = getListHtml(data);
  document.getElementById("ListGames").innerHTML = html;
}
*/