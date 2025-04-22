$("[data-mask-currency]").on("input", (event) => {
  const numericValue = event.target.value?.replace(/\D/g, "") || "0";

  const formattedValue = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  })
    .format(parseFloat(numericValue) / 100)
    .replace("R$", "")
    .trim();

  event.target.value = formattedValue;
});
