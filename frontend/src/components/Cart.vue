<template>
  <div class="container">
    <button class="home_btn button-50" @click="goHome"> HOME</button>
    <div class="header">
      <h1>장바구니</h1>
    </div>
    <hr>
    <div class="books" v-for="book in books">
      <div class="book-info">
        <div class="book-image-text">
          <div class="img"><img :src="require(`@/assets/images/book-${book.id}.jpg`)"/>
          </div>
          <div class="text">
            <div class="title">{{ book.title }}</div>
            <div class="price">{{ book.price }}원</div>
          </div>
          <div>
            <button class="button-24" @click="deleteBook(book.id)">X</button>
          </div>

        </div>
        <div class="price-count">
          <div class="total-price" v-text="`${calculate(book.price, book.count)}원`"></div>
          <div class="count-buttons">
            <button class="button-80" @click="bookCount(-1, book)">-</button>
            <div class="count">{{ book.count }}</div>
            <button class="button-80" @click="bookCount(1, book)">+</button>
          </div>
        </div>
      </div>
      <hr class="line">
    </div>
    <div class="footer">
      <div class="total-price" v-text="`총 금액 : ${calculateTotalPrice()}원`"></div>
      <router-link to="order">
        <button class="button-28" @click="goOrderPage()">주문하기</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Cart",
  data() {
    return {
      response: [],
      books: [],
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init: async function () {
      await axios.get('/book/cart')
        .then((response) => {
          this.books = response.data.data
        })
    },
    calculate: function (price, count) {
      return price * count;
    },
    calculateTotalPrice: function () {
      let totalPrice = 0;
      this.books.forEach(book => totalPrice += (book.price * book.count))

      return totalPrice;
    },
    bookCount: function (num, book) {
      book.count += num;
      if (book.count <= 0) book.count = 1;
    },
    goOrderPage: async function () {
      let map = this.books.map((book) => {
        let obj = {'id': book.id, 'count': book.count}
        return obj;
      });
      localStorage.setItem('books', JSON.stringify(map));
    },
    deleteBook: async function (id) {
      await axios.delete('/book/cart/' + id)
        .then((response) => {
          this.$router.go();
        })
    },
    goHome() {
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>
img {
  width: 130px;
  height: 200px;
}

.book-info {
  display: flex;
  justify-content: space-between;
}

.book-image-text {
  display: flex;
}

.footer {
  display: flex;
}

.count-buttons {
  display: flex;
}

.header {
  font-size: 20px;
  margin-left: 20px;
}

.books {
  margin-left: 30px;
  margin-top: 30px;
}

.text {
  margin-left: 25px;
  width: 600px;
}

.title {
  font-size: 25px;
  margin-bottom: 10px;
}

.delete-btn {
  width: 50px;
  height: 50px;
  font-size: 20px;
  border-radius: 30px;
  color: red;
  background: none;
  margin-top: 60px;
}

.price {
  font-size: 18px;
  font-weight: bold;
}

.total-price {
  font-size: 30px;
}

.price-count {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-right: 100px;
}

.count-btn {
  width: 30px;
  height: 30px;
  font-size: 20px;
  border-radius: 30px
}

.count {
  font-size: 25px;
  margin: 0 10px;
}

.footer {
  border: black solid 1px;
  width: 500px;
  position: absolute;
  right: 30px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}


/*  주문하기 버튼 css*/
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
  width: 100%;
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

/*  삭제 버튼 css*/

/* CSS */
.button-24 {
  background: #FF4742;
  border: 1px solid #FF4742;
  border-radius: 6px;
  box-shadow: rgba(0, 0, 0, 0.1) 1px 2px 4px;
  box-sizing: border-box;
  color: #FFFFFF;
  cursor: pointer;
  display: inline-block;
  font-family: nunito, roboto, proxima-nova, "proxima nova", sans-serif;
  font-size: 16px;
  font-weight: 800;
  line-height: 16px;
  min-height: 40px;
  outline: 0;
  padding: 12px 14px;
  text-align: center;
  text-rendering: geometricprecision;
  text-transform: none;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  vertical-align: middle;
}

.button-24:hover,
.button-24:active {
  background-color: initial;
  background-position: 0 0;
  color: #FF4742;
}

.button-24:active {
  opacity: .5;
}

/*  +, - 버튼 css */
/* CSS */
.button-80 {
  background: #fff;
  backface-visibility: hidden;
  border-radius: 3rem;
  border-style: solid;
  border-width: .125rem;
  box-sizing: border-box;
  color: #212121;
  cursor: pointer;
  display: inline-block;
  font-family: Circular, Helvetica, sans-serif;
  font-size: 20px;
  font-weight: 700;
  letter-spacing: -.01em;
  line-height: 1.3;
  /*padding: .875rem 1.125rem;*/
  position: relative;
  text-align: left;
  text-decoration: none;
  transform: translateZ(0) scale(1);
  transition: transform .2s;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-80:not(:disabled):hover {
  transform: scale(1.05);
}

.button-80:not(:disabled):hover:active {
  transform: scale(1.05) translateY(.125rem);
}

.button-80:focus {
  outline: 0 solid transparent;
}

.button-80:focus:before {
  content: "";
  left: calc(-1 * .375rem);
  pointer-events: none;
  position: absolute;
  top: calc(-1 * .375rem);
  transition: border-radius;
  user-select: none;
}

.button-80:focus:not(:focus-visible) {
  outline: 0 solid transparent;
}

.button-80:focus:not(:focus-visible):before {
  border-width: 0;
}

.button-80:not(:disabled):active {
  transform: translateY(.125rem);
}
</style>
