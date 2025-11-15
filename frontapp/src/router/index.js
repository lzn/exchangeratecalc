import { createRouter, createWebHistory } from 'vue-router';
import DepositCalculator from '../components/DepositCalculator.vue';
import CreditCalculator from '../components/CreditCalculator.vue';

const routes = [
  {
    path: '/',
    name: 'DepositCalculator',
    component: DepositCalculator
  },
  {
    path: '/credit',
    name: 'CreditCalculator',
    component: CreditCalculator
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
