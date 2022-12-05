<template>
  <div class="container">
    <button class="home_btn button-50" @click="goHome"> HOME</button>
    <div class="header"><h1>주문/결제</h1></div>
    <div class="main">
      <div class="customer">
        <h2 class="sub-title">사용자 정보</h2>
        <input v-model="response.email" type="email" placeholder="이메일을 입력해주세요.">
      </div>
      <div class="shipping-address">
        <h2 class="sub-title">배송지 정보</h2>
        <input v-model="response.address" type="text" placeholder="주소를 입력해주세요.">
      </div>
      <div class="product">
        <h2 class="sub-title">주문 상품</h2>
        <div class="book" v-for="book in this.response.books">
          <div class="book-info">
            <div class="img-title">
              <div><img :src="require(`@/assets/images/book-${book.id}.jpg`)"/></div>
              <div class="title">{{ book.title }}</div>
            </div>
            <div class="count">{{ book.count }}개</div>
            <div class="price" v-text="`${calculateSingleBookPrice(book.price, book.count)}원`"></div>
          </div>
        </div>
      </div>
      <div class="payment">
        <h2>결제</h2>
        <div class="total-price" v-text="`총 금액 : ${this.response.totalPrice}원`"></div>
      </div>
    </div>
    <div class="buttons">
      <button class="button-28" @click="buy">주문완료</button>
      <button class="button-28" @click="goBack">취소</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Order",
  data() {
    return {
      response: {
        'email': '',
        'address': '',
        'totalPrice': 0,
        'books': [],
      },
      books: []
    }
  },
  async mounted() {
    await this.init();
    this.calculateTotalPrice();
  },
  methods: {
    init: async function () {
      this.books = JSON.parse(localStorage.getItem('books'));
      await this.getOrderBooks()
    },
    buy: async function () {
      await axios.post('/order', this.response)
        .then((response) => {
          alert("주문 완료");
          this.$router.push('/')
        }).catch((error) => {
          alert("품절되었습니다.")
        })
    },
    calculateSingleBookPrice: function (price, count) {
      return price * count;
    },
    calculateTotalPrice: function () {
      let totalPrice = 0;
      this.response.books.forEach(book => totalPrice += (book.price * book.count));
      this.response.totalPrice = totalPrice;
    },
    getOrderBooks: async function () {
      await axios.post('/order/cart', this.books)
        .then((response) => {
          console.log(response.data.data.books);
          this.response.books = response.data.data.books;
        })
    },
    goBack() {
      this.$router.go(-1);
    },
    goHome() {
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>
img {
  width: 120px;
  height: 180px;
}

.container {
  width: 1000px;
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 430px;
}

.main {
  width: 1000px;
  left: 500px;
  text-align: center;
}

.book-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.header {
  font-size: 20px;
}

.customer, .shipping-address {
  border: black solid 1px;
  border-radius: 30px;
  height: 200px;
  padding: 1px 20px;
  margin-bottom: 30px;
}

.product {
  border: black solid 1px;
  border-radius: 30px;
  padding: 1px 20px;
  margin-bottom: 30px;
}

.img-title {
  display: flex;
  align-items: center;
  width: 400px;
}

.title {
  font-size: 25px;
  font-weight: bold;
  margin-left: 30px;
}

.count {
  font-size: 20px;
}

.price {
  font-size: 25px;
  margin-right: 30px;
}

input {
  width: 40%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
}

.payment {
  border: black solid 1px;
  border-radius: 30px;
  padding: 1px 20px;
  margin-bottom: 30px;
}

.total-price {
  font-size: 24px;
  margin-bottom: 10px;
}

.buttons {
  display: flex;
  justify-content: space-evenly;
  margin: 10px 0px 20px 0px;
}

/*버튼 css*/
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
  width: 20%;
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
