<template>
  <button class="home_btn button-50" @click="goHome"> HOME</button>
  <div class="container">
    <div class="empty"></div>
    <div class="title-image">
      <h1 class="title">{{ book.title }}</h1>
      <img v-bind:src="imagePath">
    </div>
    <div class="remain-info">
      <div class="price">{{ book.price }}원</div>
      <hr class="line">
      <div class="author">{{ book.author }}</div>
      <div class="publisher">{{ book.publisher }}</div>
      <hr class="line">
      <div class="plot">{{ book.plot }}</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "Book-Detail",
  data() {
    return {
      book: {}
    }
  },
  mounted() {
    this.init();
  },
  props: {id: ""},
  computed: {
    imagePath() {
      return require(`../assets/images/book-${this.id}.jpg`);
    }
  },
  methods: {
    init: async function () {
      await axios.get('/book/' + this.id)
        .then((response) => this.book = response.data.data);
    },
    goHome() {
      this.$router.push("/");
    }
  },
}
</script>

<style scoped>
img {
  width: 430px;
  height: 602px;
}

.container {
  display: flex;
  height: 100%;
  justify-content: center;
  align-items: center;
}

.remain-info {
  display: flex;
  flex-direction: column;
  margin-left: 70px;
  width: 400px
}

.title {
  size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 50px;
}

.price {
  font-size: 40px;
  font-weight: bolder;
  margin-bottom: 20px;
}

.author {
  font-size: 25px;
  margin-bottom: 10px;
}

.publisher {
  font-size: 15px;
  color: darkgray;
}

.plot {
  font-size: 20px;
}
</style>
