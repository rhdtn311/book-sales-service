import {createRouter, createWebHistory} from 'vue-router'

import Main from '../components/Main.vue'
import BookList from '../components/Book-list.vue'
import BookDetail from '@/components/Book-Detail';
import Cart from '../components/Cart.vue'
import Order from "@/components/Cart-order";
import OrderList from "@/components/Order-list";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'main',
      component: Main
    },
    {
      path: '/book-list',
      name: 'book-list',
      component: BookList
    },
    {
      path: '/book-detail/:id',
      name: 'book-detail',
      component: BookDetail,
      props: true
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart
    },
    {
      path: '/order',
      name: 'order',
      component: Order
    },
    {
      path: '/order-list',
      name: 'order-list',
      component: OrderList
    },
  ]
})

export default router
