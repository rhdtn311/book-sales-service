<template>
  <div class="container">
    <div class="top">
      <div>도서 주문 서비스</div>
      <router-link to="cart">
        <button class="button-27 go-cart-btn">장바구니로 이동</button>
      </router-link>
      <router-link to="order-list">
        <button class="button-27 go-order-list-btn">주문조회</button>
      </router-link>
    </div>
    <hr class="line">
    <div class="main">
      <div class="nav-bar">
        <p style="font-size: 20px">목록</p>
        <ul class="tree">
          <li><span class="click-li" @click="getBooks('ALL', 'ALL')">전체</span></li>
          <ul>
            <li class="not-click">국내도서</li>
            <ul>
              <li><span class="click-li" @click="getBooks('DOMESTIC', 'ALL')">전체</span></li>
              <li><span class="click-li" @click="getBooks('DOMESTIC', 'NOVEL')">소설</span></li>
              <li><span class="click-li" @click="getBooks('DOMESTIC', 'POETRY')">시/에세이</span></li>
              <li><span class="click-li" @click="getBooks('DOMESTIC', 'ART')">예술</span></li>
              <li><span class="click-li" @click="getBooks('DOMESTIC', 'HISTORY')">역사/문화</span></li>
              <li><span class="click-li" @click="getBooks('DOMESTIC', 'EDUCATION')">교육</span></li>
            </ul>
            <li class="not-click">해외도서</li>
            <ul>
              <li><span class="click-li" @click="getBooks('FOREIGN', 'ALL')">전체</span></li>
              <li><span class="click-li" @click="getBooks('FOREIGN', 'NOVEL')">소설</span></li>
              <li><span class="click-li" @click="getBooks('FOREIGN', 'POETRY')">시/에세이</span></li>
              <li><span class="click-li" @click="getBooks('FOREIGN', 'ART')">예술</span></li>
              <li><span class="click-li" @click="getBooks('FOREIGN', 'HISTORY')">역사/문화</span></li>
              <li><span class="click-li" @click="getBooks('FOREIGN', 'EDUCATION')">교육</span></li>
            </ul>
          </ul>
        </ul>
      </div>
      <div>
        <BookList v-bind:book_data='books'/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

import BookList from './Book-list.vue'

export default {
  name: 'Book-list',
  components: {
    BookList
  },
  data() {
    return {
      books: []
    }
  },
  methods: {
    getBooks: function (mainCategory, subCategory) {
      axios.get('/book/' + mainCategory + '/' + subCategory)
        .then((response) => {
          this.books = response.data.data;
        })
    },
  }
}
</script>

<style scoped>
li {
  list-style: none;
}

.main {
  display: flex;
}

.top {
  font-size: 50px;
  font-weight: bold;
}

.click-li:hover {
  cursor: pointer;
  text-decoration: underline #434242;
}

.not-click {
  font-size: 20px;
  color: #3C4048;
}

.container {
  margin: 0;
  padding: 0;
}

.top {
  display: flex;
  justify-content: space-between;
  width: 1900px;
}

.go-cart-btn {
  position: fixed;
  right: 30px;
  top: 20px
}

.go-order-list-btn {
  position: fixed;
  right: 230px;
  top: 20px;
}

.line {
  width: 1900px;
  margin-bottom: 0;
}

.nav-bar {
  background-color: #fff;
  border-style: none solid none none;
  border-width: 1px;
  height: 900px;
  width: 250px;
}

/*  button css */
.button-27 {
  appearance: none;
  background-color: #000000;
  border: 2px solid #1A1A1A;
  border-radius: 15px;
  box-sizing: border-box;
  color: #FFFFFF;
  cursor: pointer;
  display: inline-block;
  font-family: Roobert, -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  margin-right: 0;
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
  width: 170px;
  will-change: transform;
}

.button-27:disabled {
  pointer-events: none;
}

.button-27:hover {
  box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
  transform: translateY(-2px);
}

.button-27:active {
  box-shadow: none;
  transform: translateY(0);
}

/*  list css */
.tree,
.tree ul {
  margin: 0 0 0 1em; /* indentation */
  padding: 0;
  list-style: none;
  color: #191919;
  position: relative;
}

.tree ul {
  margin-left: 30px
}

/* (indentation/2) */

.tree:before,
.tree ul:before {
  content: "";
  display: block;
  width: 0;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  border-left: 1px solid;
}

.tree li {
  margin: 0;
  padding: 0 1.5em; /* indentation + .5em */
  line-height: 2em; /* default list item's `line-height` */
  font-weight: bold;
  position: relative;
}

.tree li:before {
  content: "";
  display: block;
  width: 10px; /* same with indentation */
  height: 0;
  border-top: 1px solid;
  margin-top: -1px; /* border top width */
  position: absolute;
  top: 1em; /* (line-height/2) */
  left: 0;
}

.tree li:last-child:before {
  background: white; /* same with body background */
  height: auto;
  top: 1em; /* (line-height/2) */
  bottom: 0;
}


</style>
