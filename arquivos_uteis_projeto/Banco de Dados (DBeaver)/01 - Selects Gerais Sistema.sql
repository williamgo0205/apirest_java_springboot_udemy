-- Selects Gerais do sistema
select * from categoria c;
select * from produto p;
select * from cliente c;
select * from venda v;
select * from item_venda iv;

-- Select dados vendas
select c.nome nome_cliente,
       cat.nome nome_categoria,
       p.descricao descricao_produto,
       p.preco_venda preco_produto,
       v.codigo codigo_venda,
       v.data data_venda,
       iv.quantidade quantidade_produto_vendido       
  from item_venda iv,
       venda v, 
       cliente c,
       produto p,
       categoria cat 
 where iv.codigo_venda = v.codigo 
   and iv.codigo_produto = p.codigo
   and v.codigo_cliente = c.codigo
   and p.codigo_categoria = cat.codigo;