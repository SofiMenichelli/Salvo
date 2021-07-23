const app = {
    data() {
        return {
            columns: [1,2,3,4,5,6,7,8,9,10],
            rows: ["A","B","C","D","E","F","G","H","I","J"],
            game: [],
            shipsGame1: [],
            ships: [],
            shipsPositions: [],
            gamePlayers: [],
            salvoesPositions: [],
            salvoesContrincant: [],
            gameContrincant: [],
            email: "",
            emailContrincant: "",
        }

    },
    created() {
        //Traigo el juego por id
        //const urlSearchParamsGames = new URLSearchParams(window.location.search);
        //const paramsGame = Object.fromEntries(urlSearchParams.entries());

        axios.get("/api/games/" + 1)
            .then(({data}) => {
                this.gamePlayers = data['Game Players']
                this.gamePlayers.sort((a,b) => parseInt(b.id) - parseInt(a.id))
                for(i in this.gamePlayers) {
                    if (this.gamePlayers[i].player.id != this.game.id){
                        this.gameContrincant = this.gamePlayers[i]
                    }
                }     
                this.emailContrincant = this.gameContrincant.player.email  
                for(i in this.gameContrincant.salvoes) {
                    for(j in this.gameContrincant.salvoes[i].Locations) {
                        this.salvoesContrincant.push(this.gameContrincant.salvoes[i].Locations[j])
                
                    }
                } 
                console.log(this.gameContrincant, "contrincante")         
            })
            .catch(err => console.log(err.response))
            
            
            //Traigo el juegador por id
            const urlSearchParams = new URLSearchParams(window.location.search);
            const params = Object.fromEntries(urlSearchParams.entries());
            
            axios.get("/api/game_view/" + params.gp)
            .then(res => {
                this.game = res.data
                this.email = this.game.player.email
                console.log(this.game, "jugador")
                this.salvoes = res.data.salvoes
                this.shipsGame1 = res.data.ships
                this.ships = this.shipsGame1.map(ship => ship.Locations)

                for (var i in this.ships) {
                    for(j in  this.ships[i]) {
                        this.shipsPositions.push(this.ships[i][j])
                    }
                }

                for (var i in this.salvoes) {
                    let turn = this.salvoes[i].turn
                    for(var j in this.salvoes[i].Locations) {
                        this.salvoesPositions.push(this.salvoes[i].Locations[j])
                        let id = document.getElementById(this.salvoes[i].Locations[j])
                        id.innerHTML = turn 
                    }
                }
            })
            .catch(err => console.log(err.response.data))
    },
    methods: {
    },
    computed: {
   /*      gamePlayerContrincant(players){
            players.forEach(player => {
                console.log(player)
                if(player.id !== this.game.player.id){
                    console.log(player.id)
                }
            })
        } */
    },
}
Vue.createApp(app).mount('#app')
