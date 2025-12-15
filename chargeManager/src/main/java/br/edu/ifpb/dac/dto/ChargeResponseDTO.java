package br.edu.ifpb.dac.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChargeResponseDTO {
    private String id;
    private String status;
    private String message;
}
