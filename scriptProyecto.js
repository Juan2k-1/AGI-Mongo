// Conectar a la base de datos
const db = connect('mongodb://localhost:27017/yelp');

// Función para convertir una cadena a un objeto JSON válido
function convertToDocumentEmbedded(str) {
  // Mostrar la cadena para depurar
  print(`Intentando convertir la cadena: ${str}`);

  // Reemplazar comillas simples por dobles para hacerla compatible con JSON
  let jsonStr = str.replace(/'/g, '"');
  
  // Eliminar el prefijo 'u' en las claves
  jsonStr = jsonStr.replace(/u"([^"]+)"/g, '"$1"');
  
  // Reemplazar los valores True/False por true/false de JSON
  jsonStr = jsonStr.replace(/True/g, 'true').replace(/False/g, 'false');
  
  // Reemplazar el valor None por null de JSON
  jsonStr = jsonStr.replace(/None/g, 'null');
  
  // Intentar parsear el JSON
  try {
    const jsonObject = JSON.parse(jsonStr);
    return jsonObject;
  } catch (e) {
    print(`Error al convertir la cadena a objeto JSON: ${str}`);
    return null; // Devolver null si no se puede convertir
  }
}

// Encontrar documentos con los campos incorrectos
const cursor = db.business.find({
  $or: [
    { "attributes.BusinessParking": { $type: "string" } },
    { "attributes.GoodForMeal": { $type: "string" } },
    { "attributes.Ambience": { $type: "string" } },
    { "attributes.Music": { $type: "string" } }
  ]
});

// Iterar sobre los documentos y corregir los valores
cursor.forEach(doc => {
  
  const updateFields = {};
  let updated = false;

  // Convertir BusinessParking de cadena a objeto embebido si es necesario
  if (typeof doc.attributes.BusinessParking === 'string') {
    const converted = convertToDocumentEmbedded(doc.attributes.BusinessParking);
    if (converted) {
      updateFields["attributes.BusinessParking"] = converted;
      updated = true;
    }
  }
  
  // Convertir GoodForMeal de cadena a objeto embebido si es necesario
  if (typeof doc.attributes.GoodForMeal === 'string') {
    const converted = convertToDocumentEmbedded(doc.attributes.GoodForMeal);
    if (converted) {
      updateFields["attributes.GoodForMeal"] = converted;
      updated = true;
    }
  }
  
  // Convertir Ambience de cadena a objeto embebido si es necesario
  if (typeof doc.attributes.Ambience === 'string') {
    const converted = convertToDocumentEmbedded(doc.attributes.Ambience);
    if (converted) {
      updateFields["attributes.Ambience"] = converted;
      updated = true;
    }
  }
  
  // Convertir Music de cadena a objeto embebido si es necesario
  if (typeof doc.attributes.Music === 'string') {
    const converted = convertToDocumentEmbedded(doc.attributes.Music);
    if (converted) {
      updateFields["attributes.Music"] = converted;
      updated = true;
    }
  }

  // Realizar la actualización si es necesario
  if (updated) {
    db.business.updateOne(
      { _id: doc._id },
      { $set: updateFields }
    );
  } else {
    print(`No se realizó actualización para el documento con _id: ${doc._id}`);
  }
});
