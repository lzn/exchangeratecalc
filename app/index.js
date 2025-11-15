const express = require('express');
const app = express();
const port = 3000;

app.use(express.static('public'));
app.use(express.json());

app.post('/calculate-interest', (req, res) => {
  const { amount, rate, duration } = req.body;

  if (typeof amount !== 'number' || typeof rate !== 'number' || typeof duration !== 'number') {
    return res.status(400).json({ error: 'Invalid input. Please provide numbers for amount, rate, and duration.' });
  }

  const interest = amount * (rate / 100) * (duration / 12);
  const futureValue = amount + interest;

  res.json({ result: futureValue });
});


const server = app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`);
});

module.exports = server;
