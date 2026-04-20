INSERT INTO aventura.aventureiros (id, ativo, classe, nivel, nome, organizacao_id, usuario_responsavel_id, created_at,
                                   updated_at)
VALUES (101, true, 'GUERREIRO', 45, 'Aragorn da Silva', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (102, true, 'MAGO', 50, 'Mestre Gandalf', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (103, false, 'LADINO', 5, 'Bilbo Bolseiro', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (104, true, 'CLERIGO', 32, 'Padre Marcelo Cavaleiro', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (105, true, 'ARQUEIRO', 40, 'Legolas Verdefolha', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (106, true, 'BERSERKER', 28, 'Gimli Filho de Gloin', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (107, true, 'GUERREIRO', 12, 'Boromir de Gondor', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (108, false, 'MAGO', 48, 'Saruman, o Branco', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (109, true, 'LADINO', 15, 'Gollum Smeagol', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (110, true, 'ARQUEIRO', 22, 'Tauriel da Floresta', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (111, true, 'CLERIGO', 18, 'Elrond Meio-Elfo', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (112, true, 'BERSERKER', 35, 'Thorin Escudo de Carvalho', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (113, true, 'GUERREIRO', 8, 'Faramir Menor', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (114, true, 'ARQUEIRO', 10, 'Haldir de Lorien', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (115, false, 'MAGO', 14, 'Radagast, o Castanho', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (116, true, 'LADINO', 20, 'Kili Anão', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (117, true, 'GUERREIRO', 25, 'Fili Anão', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (118, true, 'CLERIGO', 40, 'Galadriel Brilhante', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (119, true, 'BERSERKER', 50, 'Beorn o Transmorfo', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (120, true, 'ARQUEIRO', 30, 'Bard, o Arqueiro', 1, 1, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP) ON CONFLICT (id) DO NOTHING;

ALTER SEQUENCE aventura.aventureiros_id_seq RESTART WITH 121;

-- ====================================================================================================================

INSERT INTO aventura.companheiros (aventureiro_id, especie, lealdade, nome)
VALUES (101, 'LOBO', 100, 'Fantasma'),
       (102, 'AGUIA', 95, 'Gwaihir'),
       (105, 'CORUJA', 80, 'Edwiges'),
       (108, 'DRAGAO_MINIATURA', 40, 'Smauginho'),
       (111, 'GRIFO', 88, 'Penas de Prata'),
       (115, 'GOLEM', 70, 'Pedregulho'),
       (118, 'AGUIA', 100, 'Thorondor'),
       (119, 'LOBO', 90, 'Lobo Mau') ON CONFLICT (aventureiro_id) DO NOTHING;

-- ====================================================================================================================

--  MISSOES
INSERT INTO aventura.missoes (id, nivel_perigo, status, titulo, organizacao_id, created_at)
VALUES (101, 'EXTREMO', 'CONCLUIDA', 'Destruir o Anel do Poder', 1, CURRENT_TIMESTAMP),
       (102, 'MEDIO', 'EM_ANDAMENTO', 'Retomar Erebor', 1, CURRENT_TIMESTAMP),
       (103, 'BAIXO', 'PLANEJADA', 'Festa no Condado', 1, CURRENT_TIMESTAMP),
       (104, 'ALTO', 'CANCELADA', 'Atravessar as Minas de Moria', 1, CURRENT_TIMESTAMP),
       (105, 'GGIZI_ACHEI_FACIL', 'CONCLUIDA', 'Passeio em Valinor', 1, CURRENT_TIMESTAMP),
       (106, 'EXTREMO', 'EM_ANDAMENTO', 'Batalha do Abismo de Helm', 1, CURRENT_TIMESTAMP),
       (107, 'ALTO', 'CONCLUIDA', 'Defender Osgiliath', 1, CURRENT_TIMESTAMP),
       (108, 'MEDIO', 'PLANEJADA', 'Negociar com os Ents', 1, CURRENT_TIMESTAMP),
       (109, 'BAIXO', 'CONCLUIDA', 'Limpar as Teias da Floresta das Trevas', 1, CURRENT_TIMESTAMP),
       (110, 'ALTO', 'EM_ANDAMENTO', 'Fugir dos Nazgul', 1, CURRENT_TIMESTAMP),
       (111, 'EXTREMO', 'CONCLUIDA', 'Batalha dos Cinco Exércitos', 1, CURRENT_TIMESTAMP),
       (112, 'GGIZI_ACHEI_FACIL', 'PLANEJADA', 'Tomar chá na casa de Elrond', 1,
        CURRENT_TIMESTAMP) ON CONFLICT (id) DO NOTHING;

ALTER SEQUENCE aventura.missoes_id_seq RESTART WITH 113;

-- ====================================================================================================================

--  PARTICIPACAO
INSERT INTO aventura.participacao_missao (aventureiro_id, missao_id, papel_missao, destaque, recompensa_ouro,
                                          data_registro)
VALUES (101, 101, 'LIDER', true, 10000.00, CURRENT_TIMESTAMP),
       (102, 101, 'SUPORTE', false, 5000.00, CURRENT_TIMESTAMP),
       (105, 101, 'ATACANTE', true, 6000.00, CURRENT_TIMESTAMP),
       (106, 101, 'ATACANTE', false, 6000.00, CURRENT_TIMESTAMP),
       (107, 101, 'BATEDOR', false, 2000.00, CURRENT_TIMESTAMP),
       (112, 102, 'LIDER', true, 8000.00, CURRENT_TIMESTAMP),
       (103, 102, 'BATEDOR', true, 15000.00, CURRENT_TIMESTAMP),
       (102, 102, 'SUPORTE', false, 3000.00, CURRENT_TIMESTAMP),
       (101, 106, 'LIDER', true, 7000.00, CURRENT_TIMESTAMP),
       (105, 106, 'ATACANTE', true, 7000.00, CURRENT_TIMESTAMP),
       (106, 106, 'ATACANTE', false, 6900.00, CURRENT_TIMESTAMP),
       (110, 106, 'SUPORTE', false, 4000.00, CURRENT_TIMESTAMP),
       (112, 111, 'LIDER', false, 5000.00, CURRENT_TIMESTAMP),
       (116, 111, 'ATACANTE', false, 4500.00, CURRENT_TIMESTAMP),
       (117, 111, 'ATACANTE', false, 4500.00, CURRENT_TIMESTAMP),
       (119, 111, 'BATEDOR', true, 9000.00, CURRENT_TIMESTAMP),
       (120, 111, 'SUPORTE', true, 12000.00, CURRENT_TIMESTAMP),
       (104, 105, 'LIDER', true, 100.00, CURRENT_TIMESTAMP),
       (111, 105, 'SUPORTE', false, 100.00, CURRENT_TIMESTAMP),
       (118, 105, 'BATEDOR', false, 100.00, CURRENT_TIMESTAMP),
       (109, 104, 'BATEDOR', true, 0.00, CURRENT_TIMESTAMP),
       (113, 107, 'LIDER', false, 2000.00, CURRENT_TIMESTAMP),
       (114, 107, 'ATACANTE', true, 3500.00, CURRENT_TIMESTAMP) ON CONFLICT (aventureiro_id, missao_id) DO NOTHING;