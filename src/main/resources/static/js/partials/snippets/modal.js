$(".modal").on("hidden.bs.modal", (event) => {
  $(event.target).find("form").removeClass("was-validated").trigger("reset");
});
