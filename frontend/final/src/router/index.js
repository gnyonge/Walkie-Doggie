import Vue from 'vue'
import VueRouter from 'vue-router'
import Calendar from '../views/Calendar.vue'
import Walk from '../views/Walk.vue'
import Food from '../views/Food.vue'
import Community from '../views/Community.vue'
import MyPage from '../views/mypage/MyPage.vue'
import Login from '../views/Login.vue'
import LoginMain from '../views/LoginMain.vue'
import Signup from '../views/Signup.vue'
import Register from '../views/register/Register.vue'
import DogRegister from '../views/register/DogRegister.vue'
import DogRegister2 from '../views/register/DogRegister2.vue'
import DogChange from '../views/mypage/DogChange.vue'
import Health from '../views/mypage/Health.vue'
import InfoChange from '../views/mypage/InfoChange.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Calendar
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar
  },
  {
    path: '/walk',
    name: 'Walk',
    component: Walk
  },
  {
    path: '/food',
    name: 'Food',
    component: Food
  },
  {
    path: '/community',
    name: 'Community',
    component: Community
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/loginmain',
    name: 'LoginMain',
    component: LoginMain
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/dogregister',
    name: 'DogRegister',
    component: DogRegister
  },
  {
    path: '/dogregisternext',
    name: 'DogRegister2',
    component: DogRegister2
  },
  {
    path: '/dogchange',
    name: 'DogChange',
    component: DogChange
  },
  {
    path: '/health',
    name: 'Health',
    component: Health
  },
  {
    path: '/infochange',
    name: 'InfoChange',
    component: InfoChange
  },
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
