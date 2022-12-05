import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index.js'

const app = createApp(App)

app.use(router)

app.mount('#app')

app.config.globalProperties.server = "http://localhost:8080"
