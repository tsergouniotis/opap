select number_1,count(number_1) as freq from draws group by number_1 order by freq asc;
select number_2,count(number_2) as freq from draws group by number_2 order by freq asc;
select number_3,count(number_3) as freq from draws group by number_3 order by freq asc;
select number_4,count(number_4) as freq from draws group by number_4 order by freq asc;
select number_5,count(number_5) as freq from draws group by number_5 order by freq asc;
select joker,count(joker) as freq from draws group by joker order by freq asc;

select * from number_frequencies order by frequency asc;


select number, frequency, frequency/(select sum(frequency) from number_frequencies)::float from number_frequencies group by frequency,number order by frequency asc;

