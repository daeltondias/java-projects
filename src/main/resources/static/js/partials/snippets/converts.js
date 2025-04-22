function currencyToFloat(currency) {
  let value = String(currency).replace("R$", "").trim();
  value = value.replace(/\./g, "").replace(",", ".");
  return parseFloat(value);
}

function floatToCurrency(value) {
  const formattedValue = Number(value).toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
  return formattedValue.replace("NaN", "0,00");
}
