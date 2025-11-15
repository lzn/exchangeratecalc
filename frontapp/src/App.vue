<script setup>
import { ref } from 'vue';

const amount = ref(null);
const rate = ref(null);
const duration = ref(null);
const result = ref('');

const calculateInterest = async () => {
  try {
    const response = await fetch('http://localhost:3000/calculate-interest', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        amount: parseFloat(amount.value),
        rate: parseFloat(rate.value),
        duration: parseInt(duration.value)
      })
    });

    const data = await response.json();

    if (response.ok) {
      result.value = `Future Value: ${data.result.toFixed(2)}`;
    } else {
      result.value = `Error: ${data.error}`;
    }
  } catch (error) {
    result.value = 'An error occurred while calculating.';
    console.error('Fetch error:', error);
  }
};
</script>

<template>
  <h1>Bank Deposit Interest Calculator</h1>
  <form @submit.prevent="calculateInterest">
    <label for="amount">Amount:</label>
    <input type="number" id="amount" v-model="amount" required><br><br>

    <label for="rate">Interest Rate (%):</label>
    <input type="number" id="rate" v-model="rate" step="0.01" required><br><br>

    <label for="duration">Duration (months):</label>
    <input type="number" id="duration" v-model="duration" required><br><br>

    <button type="submit">Calculate</button>
  </form>

  <h2>Result:</h2>
  <p>{{ result }}</p>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  width: 300px;
}
label {
  margin-top: 10px;
}
button {
  margin-top: 20px;
}
</style>
