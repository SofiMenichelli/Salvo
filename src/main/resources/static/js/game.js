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
            shipsContrincant: [],
            shipsPositionsContricant: [],
        }

    },
    created() {
        
        /* axios.post('/api/login', "email=melba@mindhub.com&password=melba", { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).then(response => console.log('signed in!!!')) */

        //Traigo el juegador por id
        const urlSearchParams = new URLSearchParams(window.location.search);
        const params = Object.fromEntries(urlSearchParams.entries());
        
        axios.get("/api/game_view/" + params.gp)
        .then(res => {
            this.game = res.data
            this.email = this.game.player.email
            this.salvoes = res.data.salvoes
            this.shipsGame1 = res.data.ships
            
            //Creo un array con las posiciones de los barcos
            this.ships = this.shipsGame1.map(ship => ship.Locations)
            for (var i in this.ships) {
                for(j in  this.ships[i]) {
                    this.shipsPositions.push(this.ships[i][j])
                }
            }

            //Creo un array con las locaciones de mis ataques
            for (var i in this.salvoes) {
                let turn = this.salvoes[i].turn
                for(var j in this.salvoes[i].Locations) {
                    this.salvoesPositions.push(this.salvoes[i].Locations[j])
                }
            }
        })
        .then(res => {
            //Traigo el juego por id
            //const urlSearchParamsGames = new URLSearchParams(window.location.search);
            //const paramsGame = Object.fromEntries(urlSearchParams.entries());
            axios.get("/api/games/" + 1)
                .then(({ data }) => {
                    this.gamePlayers = data['Game Players']
                    this.gamePlayers.sort((a, b) => parseInt(b.id) - parseInt(a.id))

                    //Busco el enemigo
                    for (i in this.gamePlayers) {
                        if (this.gamePlayers[i].player.id != this.game.id) {
                            this.gameContrincant = this.gamePlayers[i]
                        }
                    }
                    this.emailContrincant = this.gameContrincant.player.email
                    //Busco los disparos del enemigo a mis barcos
                    for (i in this.gameContrincant.salvoes) {
                        let turn = this.gameContrincant.salvoes[i].turn
                        for (j in this.gameContrincant.salvoes[i].Locations) {
                            this.salvoesContrincant.push(this.gameContrincant.salvoes[i].Locations[j])
                        }
                    }

                    //Busco las posiciones de los barcos del enemigo
                    this.shipsContrincant = this.gameContrincant.ships.map(e => e.Locations)
                    for (var i in this.shipsContrincant) {
                        for (j in this.shipsContrincant[i]) {
                            this.shipsPositionsContricant.push(this.shipsContrincant[i][j])
                        }
                    }
                })
                .catch(err => console.log(err.response))
        })
        .catch(err => console.log(err.response.data))
    },
    methods: {
        logOut() {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(error => console.log(error.response.data))
        }
    },
    computed: {

    },
}
Vue.createApp(app).mount('#app')
