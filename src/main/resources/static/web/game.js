
fetch("/api/game_view/" + gp()).then(function (response) {
    if (response.ok) {
        return response.json();
    }
    throw new Error(response.statusText);
}).then(function (json) {

        data= json
        console.log(data);

        renderList(data);



}).catch(function (error) {
    console.log("Request failed:" + error.message);
});


function gp(){

   var url_string = window.location.href
   var url = new URL(url_string);
   var gp = url.searchParams.get("gp");
   console.log(gp);

   return gp;
}


function getItemHtml(data) {
        console.log(data);
        for (i = 0; i < data.gamePlayers[i].length; i++) {
        return "<li>" + data.gamePlayers[0].player.email + "(you)" + " vs " + (data.gamePlayers[1] ? data.gamePlayers[1].player.email : "Wait for player") + "</li>";
        }
        }


        function getListHtml(data) {
          return data.gamePlayers.map(getItemHtml).join("");
        }

        function renderList(data) {
            var html = getListHtml(data);
            document.getElementById("ListGames").innerHTML = html;
        }

/*function getItemHtml(ListGames) {   fORMA normal table javascript working
console.log(ListGames);

var ol = document.createElement("ol");
var li = document.createElement("li");

if (ListGames.gamePlayers.length > 1) {

for (i = 0; i < ListGames.gamePlayers.length; i++) {

var player = ListGames.gamePlayers[i].player.email;


li.append(player);
ol.append(li);

}
}

else{
        var ol = document.createElement("ol");
        var li = document.createElement("li");



        var playerMsg = (ListGames.gamePlayers[0].player.email) + " vs " + "waiting for player";


        li.append(playerMsg);
        ol.append(li);
    }
return ol.innerHTML;

}*/




var tableGrid = new Vue({
    el: "#tableGrid",
    data: {
        numbers: ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        letters: ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        gp: "",
        gameData:"",
        GamePlayer_1:"",
        GamePlayer_2:"",

    },



    methods: {

        fetch: function () {

            fetch("/api/game_view/" + this.gp).then(response => {

                if (response.ok) {
                    return response.json();
                }
                throw new Error(response.statusText);
            }).then(json => {

                this.gameData = json;
                console.log(this.gameData);

               this.shipsLocation(this.gameData);
               this.SalvoOpponentLocation(this.gameData);
               this.MySalvoLocations(this.gameData);
                //this.renderList(this.data);


            }).catch(function (error) {
                console.log("Request failed:" + error.message);
            });

        },

        gp: function () {

            var url = new URL(window.location.href);
            this.gp = url.searchParams.get("gp");
            this.fetch();
        },

        
        shipsLocation: function (gameData) {

            this.gameData.ships.forEach(function (ship) {
                ship.locations.forEach(function (location) {
                    if(ship.locations.length==3){
                    var s = document.getElementById(location).classList.add("ship");
                    }
                    if(ship.locations.length==2){
                    var s = document.getElementById(location).classList.add("ships");    
                    }
                    
                });
            });
        },

        SalvoOpponentLocation: function(gameData) {
        this.gameData.salvoesOpponent.forEach(function (salvo){
        salvo.locations.forEach(function (location){
        //if(salvo.locations == location){

        var sa = document.getElementById(location).innerHTML="X";    //.classList.add("OpponentHit");
        //}
        /*if(salvo.locations !== location){  NOT WORKING

                var sa = document.getElementById(location).classList.add("NoOpponentHit");
                }*/

        });
        });

        },

        MySalvoLocations: function(gameData){

        this.gameData.salvoes.forEach(function(salvo){
        console.log(salvo);
        salvo.locations.forEach(function(location){

        var MySalvo = document.getElementById(location + 's').innerHTML= "X";


        });


        });

        },


        GamePlayerInfo: function(gameData){
        // era para los players, no esta hecho así.
        },


        
        /*function getItemHtml(data) {
        console.log(data);
        return "<li>" + data.gamePlayers[0].player.email + "(you)" + " vs " + (data.gamePlayers[1] ? data.gamePlayers[1].player.email : "Wait for player") + "</li>";
        },

        function getListHtml(data) {
          return data.gamePlayers.map(getItemHtml).join("");
        },

        function renderList(data) {
            var html = getListHtml(data);
            document.getElementById("ListGames").innerHTML = html;
        },*/
        

    },

    
created:function(){
    
    this.gp();

},




});
