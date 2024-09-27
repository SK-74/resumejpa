package com.resumejpa.specification;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.resumejpa.entity.Member;

public class MemberSpecification {

	/**
	 * 誕生日>=指定日
	 * @param from
	 * @return
	 */
	public static Specification<Member> birthGreaterThanOrEqualTo(LocalDate from) {
		return (root, query, criteriaBuilder) -> {
			if(Objects.nonNull(from)) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get("birth"), from);
			}else {
				return null;
			}
		};
	}

	/**
	 * 誕生日<=指定日
	 * @param to
	 * @return
	 */
	public static Specification<Member> birthLessThanOrEqualTo(LocalDate to) {
		return (root, query, criteriaBuilder) -> {
			if(Objects.nonNull(to)) {
				return criteriaBuilder.lessThanOrEqualTo(root.get("birth"), to);
			}else {
				return null;
			}
		};
	}
}
