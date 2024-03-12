package br.edu.ifpb.dac.ecommerce.business.mapper;

public interface Mapper<I, O> {
    O map(I input);
}
