const app = {
    data() {
        return {
            games: [],

        }
      
    },
    created() {
        axios.get("/api/games")
        .then(res => this.games = res.data)
        .catch(err => console.log(err.response.data))
    },
    methods(){

    },
    computed(){

    }
}
Vue.createApp(app).mount('#app')
