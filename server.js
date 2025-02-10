const express = require("express");
const sqlite3 = require("sqlite3").verbose();
const bodyParser = require("body-parser");
const cors = require("cors");

const app = express();
const port = 3000;

// Middlewares
app.use(bodyParser.json());
app.use(cors());

// Banco de Dados
const db = new sqlite3.Database("./movies.db", (err) => {
  if (err) console.error("Erro ao conectar ao banco:", err.message);
  else console.log("✅ Conectado ao banco de dados SQLite");
});

// Criar tabela se não existir
db.run(
  `CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    synopsis TEXT NOT NULL,
    releasedate TEXT NOT NULL,
    imageurl TEXT NOT NULL
  )`,
  (err) => {
    if (err) console.error("Erro ao criar tabela:", err.message);
    else addInitialMovies();
  }
);

// Função para inserir filmes iniciais
function addInitialMovies() {
  db.get("SELECT COUNT(*) AS count FROM movies", (err, row) => {
    if (err) return console.error("Erro ao verificar tabela:", err.message);
    if (row.count >= 30) return; // Evita duplicação

    const movies = [
      {
        title: "A Viagem de Chihiro",
        synopsis: "Uma garota fica presa em um mundo mágico e precisa trabalhar em um banho público para espíritos para salvar seus pais.",
        releasedate: "2001-07-20",
        imageurl: "https://image.tmdb.org/t/p/w500/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg"
      },
      {
        title: "O Castelo Animado",
        synopsis: "Sophie é amaldiçoada por uma bruxa e busca ajuda no castelo de um misterioso mago chamado Howl.",
        releasedate: "2004-11-20",
        imageurl: "https://image.tmdb.org/t/p/w500/6p0yaG0iMGDspFxelW4V6FfD7cP.jpg"
      },
      {
        title: "Your Name",
        synopsis: "Dois jovens trocam de corpo misteriosamente e tentam se encontrar.",
        releasedate: "2016-08-26",
        imageurl: "https://image.tmdb.org/t/p/w500/q719jXXEzOoYaps6babgKnONONX.jpg"
      },
      {
        title: "O Tempo com Você",
        synopsis: "Um jovem encontra uma garota que pode controlar o clima e juntos enfrentam desafios em Tóquio.",
        releasedate: "2019-07-19",
        imageurl: "https://image.tmdb.org/t/p/w500/qgrk7r1fV4IjuoeiGS5HOhXNdLJ.jpg"
      },
      {
        title: "A Garota que Conquistou o Tempo",
        synopsis: "Uma estudante descobre que pode viajar no tempo, mas aprende que cada escolha tem consequências.",
        releasedate: "2006-07-15",
        imageurl: "https://image.tmdb.org/t/p/w500/zW7crUOjw7vOLtdoxlxLNuiAo90.jpg"
      },
      {
        title: "Paprika",
        synopsis: "Uma cientista usa um dispositivo para entrar nos sonhos das pessoas e combater uma ameaça que pode destruir a realidade.",
        releasedate: "2006-11-25",
        imageurl: "https://image.tmdb.org/t/p/w500/eewZmG8kCew3ywvNOGVxieDeoBK.jpg"
      },
      {
        title: "Akira",
        synopsis: "Em um futuro cyberpunk, um jovem descobre poderes psíquicos que podem mudar o destino da humanidade.",
        releasedate: "1988-07-16",
        imageurl: "https://image.tmdb.org/t/p/w500/5d6fHcEsl4CDhpJ49LZ3XcvBxze.jpg"
      },
      {
        title: "Vidas ao Vento",
        synopsis: "A história do engenheiro que projetou os aviões japoneses usados na Segunda Guerra Mundial.",
        releasedate: "2013-07-20",
        imageurl: "https://image.tmdb.org/t/p/w500/jfwSexzlIzaOgxE4LGG4zkN6j0z.jpg"
      },
    
      { title: "O Ladrão",
        synopsis: "Um ladrão que rouba segredos dos sonhos.",
        releasedate: "2010-07-16",
        imageurl: "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg" 
        },

      { title: "Interstellar",
        synopsis: "Uma equipe viaja pelo espaço para salvar a humanidade.",
        releasedate: "2014-11-07",
        imageurl: "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg" 
        },

      { title: "Duna",
        synopsis: "Paul Atreides enfrenta desafios em um planeta desértico.",
        releasedate: "2021-10-22",
        imageurl: "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg" 
        },

      { title: "John Wick 4",
        synopsis: "John Wick enfrenta novos desafios mortais.",
        releasedate: "2023-03-24",
        imageurl: "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg" 
        },

      {
        title: "O Poderoso Chefão",
        synopsis: "A história da família mafiosa Corleone e seu patriarca Vito Corleone.",
        releasedate: "1972-03-24",
        imageurl: "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"
        },
      {
        title: "Forrest Gump",
        synopsis: "A vida de Forrest Gump, um homem simples que testemunha grandes eventos da história dos EUA.",
        releasedate: "1994-07-06",
        imageurl: "https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg"
        },
      {
        title: "Clube da Luta",
        synopsis: "Um homem insatisfeito encontra um carismático vendedor de sabonetes e funda um clube de luta secreto.",
        releasedate: "1999-10-15",
        imageurl: "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg"
        },
      {
        title: "Gladiador",
        synopsis: "Um general romano traído luta como gladiador para se vingar do imperador corrupto.",
        releasedate: "2000-05-05",
        imageurl: "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg"
        },
      {
        title: "Pulp Fiction",
        synopsis: "Histórias interligadas de criminosos e assassinos de aluguel em Los Angeles.",
        releasedate: "1994-10-14",
        imageurl: "https://image.tmdb.org/t/p/w500/fIE3lAGcZDV1G6XM5KmuWnNsPp1.jpg"
        },
      {
        title: "A Origem",
        synopsis: "Um ladrão que rouba segredos dos sonhos é desafiado a plantar uma ideia na mente de alguém.",
        releasedate: "2010-07-16",
        imageurl: "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg"
        },
      {
        title: "Coração Valente",
        synopsis: "A luta de William Wallace pela independência da Escócia contra os ingleses.",
        releasedate: "1995-05-24",
        imageurl: "https://image.tmdb.org/t/p/w500/or1gBugydmjToAEq7OZY0owwFk.jpg"
        },
      {
        title: "O Silêncio dos Inocentes",
        synopsis: "Uma agente do FBI busca ajuda de um assassino canibal para capturar outro serial killer.",
        releasedate: "1991-02-14",
        imageurl: "https://image.tmdb.org/t/p/w500/uS9m8OBk1A8eM9I042bx8XXpqAq.jpg"
        },
      {
        title: "Matrix",
        synopsis: "Um hacker descobre que o mundo que conhece é uma simulação controlada por máquinas.",
        releasedate: "1999-03-31",
        imageurl: "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"
        },
      {
        title: "De Volta para o Futuro",
        synopsis: "Um jovem viaja no tempo com a ajuda de um cientista excêntrico.",
        releasedate: "1985-07-03",
        imageurl: "https://image.tmdb.org/t/p/w500/fNOH9f1aA7XRTzl1sAOx9iF553Q.jpg"
        },
      {
        title: "O Resgate do Soldado Ryan",
        synopsis: "Um grupo de soldados americanos busca um paraquedista desaparecido na Normandia.",
        releasedate: "1998-07-24",
        imageurl: "https://image.tmdb.org/t/p/w500/nVxhJ26XqGzf48lgZqmzoP9js1e.jpg"
        },
      {
        title: "O Lobo de Wall Street",
        synopsis: "A ascensão e queda de Jordan Belfort, um corretor da bolsa de valores.",
        releasedate: "2013-12-25",
        imageurl: "https://image.tmdb.org/t/p/w500/34m2tygAYBGqA9MXKhRDtzYd4MR.jpg"
        },
      {
        title: "Django Livre",
        synopsis: "Um ex-escravo se torna caçador de recompensas e busca resgatar sua esposa.",
        releasedate: "2012-12-25",
        imageurl: "https://image.tmdb.org/t/p/w500/2oZklIzUbvZXXzIFzv7Hi68d6xf.jpg"
      },
      {
        title: "O Exorcista",
        synopsis: "Uma menina é possuída por uma entidade demoníaca, e um padre tenta exorcizá-la.",
        releasedate: "1973-12-26",
        imageurl: "https://image.tmdb.org/t/p/w500/4ucLGcXVVSVnsfkGtbLY4XAius8.jpg"
        },
      {
        title: "It: A Coisa",
        synopsis: "Crianças enfrentam um palhaço demoníaco em uma pequena cidade.",
        releasedate: "2017-09-05",
        imageurl: "https://image.tmdb.org/t/p/w500/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg"
        },
      {
        title: "O Iluminado",
        synopsis: "Um escritor enlouquece em um hotel assombrado durante o inverno.",
        releasedate: "1980-05-23",
        imageurl: "https://image.tmdb.org/t/p/w500/9fgh3Ns1iRzlQNYuJyK0ARQZU7w.jpg"
        },
      {
        title: "Cavaleiro da Lua",
        synopsis: "Um homem com múltiplas personalidades se torna um vigilante poderoso.",
        releasedate: "2022-03-30",
        imageurl: "https://image.tmdb.org/t/p/w500/9nP9A4IAjHgeBpAGnA47r0Ob5hX.jpg"
        },
      {
        title: "Homem de Ferro",
        synopsis: "Tony Stark cria uma armadura para se tornar o super-herói Homem de Ferro.",
        releasedate: "2008-04-30",
        imageurl: "https://image.tmdb.org/t/p/w500/78lPtwv72eTNqFW9COBYI0dWDJa.jpg"
        },
      {
        title: "Velozes e Furiosos",
        synopsis: "Uma gangue de corredores de rua se envolve em um jogo perigoso de velocidade e crime.",
        releasedate: "2001-06-22",
        imageurl: "https://image.tmdb.org/t/p/w500/gqY0ITBgT7A5oZu5nQsk0Bgaa4I.jpg"
        },
      {
        title: "Mad Max: Estrada da Fúria",
        synopsis: "Em um deserto pós-apocalíptico, Max se junta a uma rebelião contra um tirano.",
        releasedate: "2015-05-13",
        imageurl: "https://image.tmdb.org/t/p/w500/8tZYtuWezp8JbcsvHYO0O46tFbo.jpg",
      }
   
    ];

    const stmt = db.prepare("INSERT INTO movies (title, synopsis, releasedate, imageurl) VALUES (?, ?, ?, ?)");
    movies.forEach((movie) => stmt.run(movie.title, movie.synopsis, movie.releasedate, movie.imageurl));
    stmt.finalize();
    console.log("🎬 Filmes adicionados.");
  });
}

// 📌 ROTA: Buscar todos os filmes
app.get("/movies", (req, res) => {
  db.all("SELECT * FROM movies", (err, rows) => {
    if (err) return res.status(500).json({ error: "Erro ao buscar filmes" });
    res.json(rows);
  });
});

// 📌 ROTA: Buscar um filme pelo ID
app.get("/movies/:id", (req, res) => {
  const { id } = req.params;
  console.log("Buscando filme com ID:", id); // Debugando
  db.get("SELECT * FROM movies WHERE id = ?", [id], (err, row) => {
    if (err) return res.status(500).json({ error: "Erro ao buscar filme" });
    if (!row) return res.status(404).json({ error: "Filme não encontrado" });
    res.json(row);
  });
});


// 📌 ROTA: Adicionar um novo filme
app.post("/movies", (req, res) => {
  const { title, synopsis, releasedate, imageurl } = req.body;
  if (!title || !synopsis || !releasedate || !imageurl) {
    return res.status(400).json({ error: "Todos os campos são obrigatórios." });
  }
  db.run(
    "INSERT INTO movies (title, synopsis, releasedate, imageurl) VALUES (?, ?, ?, ?)",
    [title, synopsis, releasedate, imageurl],
    function (err) {
      if (err) return res.status(500).json({ error: "Erro ao adicionar filme." });
      res.status(201).json({ id: this.lastID, title, synopsis, releasedate, imageurl });
    }
  );
});

// 📌 ROTA: Atualizar um filme pelo ID
app.put("/movies/:id", (req, res) => {
  const { id } = req.params;
  const { title, synopsis, releasedate, imageurl } = req.body;
  if (!title || !synopsis || !releasedate || !imageurl) {
    return res.status(400).json({ error: "Todos os campos são obrigatórios." });
  }
  db.run(
    "UPDATE movies SET title = ?, synopsis = ?, releasedate = ?, imageurl = ? WHERE id = ?",
    [title, synopsis, releasedate, imageurl, id],
    function (err) {
      if (err) return res.status(500).json({ error: "Erro ao atualizar filme." });
      if (this.changes === 0) return res.status(404).json({ error: "Filme não encontrado." });
      res.json({ message: "Filme atualizado com sucesso!" });
    }
  );
});

// 📌 ROTA: Deletar um filme pelo ID
app.delete("/movies/:id", (req, res) => {
  const { id } = req.params;
  db.run("DELETE FROM movies WHERE id = ?", [id], function (err) {
    if (err) return res.status(500).json({ error: "Erro ao deletar filme." });
    if (this.changes === 0) return res.status(404).json({ error: "Filme não encontrado." });
    res.json({ message: "Filme deletado com sucesso!" });
  });
});

// Iniciar o servidor
app.listen(port, () => {
  console.log(`🚀 Servidor rodando em http://localhost:${port}`);
});
