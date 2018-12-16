import Vue from 'vue';
import Router from 'vue-router';
import Account from '@/pages/Account';
import Home from '@/pages/Home/';
import Login from '@/pages/Login/';
import SignUp from '@/pages/SignUp';
import Welcome from '@/pages/Welcome';
import Category from '@/pages/Registers/Category';
import { validateToken } from '@/services/auth';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter(to, from, next) {
        const token = localStorage.getItem('token');
        if (token) {
          validateToken(JSON.stringify(token)).then(response => {
            if (response === true) {
              return next({ path: '/home'});   
            } else {
              delete localStorage.token;
              return next({ path: '/login'});
            }
          }).catch(function(error) {
            throw error;
          })
        } else {
          return next();
        }
      },
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {requiresAuth: true}
    },
    {
      path: '/account',
      name: 'Account',
      component: Account,
      meta: {requiresAuth: true}
    },
    {
      path: '/categories',
      name: 'Category',
      component: Category,
      meta: {requiresAuth: true}
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    const token = localStorage.getItem('token');
    if (token) {
      validateToken(JSON.stringify(token)).then(response => {
        if (response === true) {
          return next();
        } else {
          delete localStorage.token;
          return next({ path: '/login'});
        }
      }).catch(function(error) {
        throw error;
      })
    } else {
      return next({path: '/login'});   
    }
  } else {
    return next();
  }
});

export default router;