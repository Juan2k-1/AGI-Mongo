document.addEventListener("DOMContentLoaded", function () {
    const radios = document.querySelectorAll('input[name="selectedBusiness"]');
    const updateButton = document.getElementById('updateButton');
    const deleteButton = document.getElementById('deleteButton');
    let selectedId = '';

    // Escucha el cambio de selección en los radios
    radios.forEach(radio => {
        radio.addEventListener('change', () => {
            selectedId = radio.value;
            updateButton.disabled = false;
            deleteButton.disabled = false;
        });
    });

    // Acción del botón Modificar
    updateButton.addEventListener('click', function () {
        if (selectedId) {
            window.location.href = '/business/update/' + selectedId;
        }
    });

    // Acción del botón Eliminar
    deleteButton.addEventListener('click', function () {
        if (selectedId) {
            if (confirm('¿Estás seguro de que quieres eliminar este negocio?')) {
                window.location.href = '/business/delete/' + selectedId;
            }
        }
    });
});


