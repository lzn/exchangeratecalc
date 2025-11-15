<template>
  <div class="container">
    <h1>Kalkulator kredytu</h1>
    <form @submit.prevent="calculateCredit">
      <label for="creditAmount">
        Kwota kredytu
        <input type="number" id="creditAmount" v-model="creditAmount" required>
      </label>
      <label for="creditRate">
        Oprocentowanie (%)
        <input type="number" id="creditRate" v-model="creditRate" step="0.01" required>
      </label>
      <label for="creditDuration">
        Długość kredytu (w miesiącach)
        <input type="number" id="creditDuration" v-model="creditDuration" required>
      </label>
      <button type="submit">Oblicz</button>
    </form>
    <div v-if="schedule">
      <hr>
      <h3>Harmonogram spłat:</h3>
      <table>
        <thead>
          <tr>
            <th>Miesiąc</th>
            <th>Rata</th>
            <th>Odsetki</th>
            <th>Kapitał</th>
            <th>Pozostało</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in schedule" :key="row.month">
            <td>{{ row.month }}</td>
            <td>{{ row.payment }}</td>
            <td>{{ row.interest }}</td>
            <td>{{ row.principal }}</td>
            <td>{{ row.remaining }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const creditAmount = ref(null);
const creditRate = ref(null);
const creditDuration = ref(null);
const schedule = ref(null);

const calculateCredit = () => {
  const amount = parseFloat(creditAmount.value);
  const rate = parseFloat(creditRate.value) / 100 / 12;
  const months = parseInt(creditDuration.value);

  const monthlyPayment = (amount * rate) / (1 - Math.pow(1 + rate, -months));

  let remaining = amount;
  const scheduleData = [];

  for (let i = 1; i <= months; i++) {
    const interest = remaining * rate;
    const principal = monthlyPayment - interest;
    remaining -= principal;

    scheduleData.push({
      month: i,
      payment: monthlyPayment.toFixed(2),
      interest: interest.toFixed(2),
      principal: principal.toFixed(2),
      remaining: remaining.toFixed(2)
    });
  }
  schedule.value = scheduleData;
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
th {
  background-color: #f2f2f2;
}
</style>
