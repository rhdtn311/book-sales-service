<template>
  <div class="sub-container">
    <div class="main">
      <div class="book-info" v-for="book in book_data.length === 0 ? books : book_data">
        <div class="book-image-text">
          <div class="book-img">
            <router-link :to="{name: 'book-detail', params: {id: book.id}}">
              <img :src="require(`@/assets/images/book-${book.id}.jpg`)"/>
            </router-link>
          </div>
          <div class="book-text">
            <div class="title">
              <router-link :to="{name: 'book-detail', params: {id: book.id}}">{{ book.title }}</router-link>
            </div>
            <div class="author">{{ book.author }}</div>
            <div class="publisher">{{ book.publisher }}</div>
            <div class="price">{{ book.price }}원</div>
            <button class="button-28" @click="putCart(book.id)">장바구니</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Book-list',
  data() {
    return {
      books: [],
    }
  },
  mounted() {
    this.init()
  },
  props: {book_data: {}},
  methods: {
    init: function () {
      if (this.book_data.length === 0) {
        axios.get('/book/ALL/ALL')
          .then((response) => {
            this.books = response.data.data;
          })
      }
    },
    putCart: function (id) {
      axios.post('/book/cart/' + id)
        .then((response) => alert('장바구니에 도서를 담았습니다.'))
    }
  },
}
</script>

<style scoped>
img {
  width: 220px;
  height: 350px;
}

.book-image-text {
  display: flex;
}

.buttons {
  display: flex;
  flex-direction: column;
}

a {
  text-decoration: none;
  color: black;
}

/*  레이아웃 */
.sub-container {
  margin-top: 20px;
  margin-left: 50px;
}

.book-image-text {
  margin-bottom: 20px;
}

.book-text {
  margin-left: 40px;
}

.title {
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 10px;
}

.author {
  font-size: 23px;
}

.publisher {
  font-size: 23px;
  margin-bottom: 10px;
}

.price {
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 40px;
}

.cart-add-btn {
  width: 100px;
  height: 70px;
  font-size: 20px;
  background: bisque;
}

.cart-add-btn:hover {
  cursor: pointer;
}

/*  button css */
.button-28 {
  appearance: none;
  background-color: transparent;
  border: 2px solid #1A1A1A;
  border-radius: 15px;
  box-sizing: border-box;
  color: #3B3B3B;
  cursor: pointer;
  display: inline-block;
  font-family: Roobert, -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  margin: 0;
  min-height: 60px;
  min-width: 0;
  outline: none;
  padding: 16px 24px;
  text-align: center;
  text-decoration: none;
  transition: all 300ms cubic-bezier(.23, 1, 0.32, 1);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: 120px;
  will-change: transform;
}

.button-28:disabled {
  pointer-events: none;
}

.button-28:hover {
  color: #fff;
  background-color: #1A1A1A;
  box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
  transform: translateY(-2px);
}

.button-28:active {
  box-shadow: none;
  transform: translateY(0);
}
</style>
