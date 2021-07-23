const app = {
    data() {
        return {
            games: [],
            gamePlayers: [],
            game:[],
            shipsA: [],
            shipsB: [],

        }
      
    },
    created() {
        axios.get("/api/games")
        .then(res => {
            this.games = res.data
            this.gamePlayers = res.data[0]['Game Players']
            console.log(this.gamePlayers)
        }
            )
        .catch(err => console.log(err.response.data))

    },
    methods(){

    },
    computed(){

    }
}
Vue.createApp(app).mount('#app')
