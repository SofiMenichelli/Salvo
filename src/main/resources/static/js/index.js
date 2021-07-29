const app = {
    data() {
        return {
            username: "",
            name:"",
            psw: "",
            singUp: false,
            singA: "Sing Up",
            sing: "Sing In"
        }

    },
    created() {

    },
    methods: {
        singUpOrIn(){
            this.singUp = !this.singUp;
            if(this.singUp){
                this.singA = "Sing In"
                this.sing ="Sing Up"
            } else {
                this.singA = "Sing Up"
                this.sing = "Sing In"
            }
        },
        login(sing){
            if(sing == "Sing In") {
                axios.post('/api/login', "user=" + this.username + "&password=" + this.psw, 
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => window.location.href = "/web/games.html")
                    .catch(err => console.log(err.response))
            } 
            if(sing == "Sing Up") {
                axios.post('/api/players', "user=" + this.username + "&password=" + this.psw, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
                        window.location.href ="/web/games.html"
                        console.log('register!!!')
                    })
                    .catch(err => console.log(err.response))
            }
        }
    },
    computed: {

    },
}
Vue.createApp(app).mount('#app')