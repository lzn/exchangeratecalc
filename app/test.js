const request = require('supertest');
const server = require('./index');

describe('POST /calculate-interest', () => {
  afterAll((done) => {
    server.close(done);
  });

  it('should return the correct interest calculation', async () => {
    const res = await request(server)
      .post('/calculate-interest')
      .send({
        amount: 100,
        rate: 5,
        duration: 12
      });
    expect(res.statusCode).toEqual(200);
    expect(res.body.result).toEqual(105);
  });
});
