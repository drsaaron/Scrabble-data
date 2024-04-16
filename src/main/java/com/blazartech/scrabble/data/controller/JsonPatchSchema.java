/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author aar1069
 */
public class JsonPatchSchema {
    @NotBlank
    public Op op;

    public enum Op {
        replace, add, remove, copy, move, test
    }

    @NotBlank
    @Schema(example = "/name")
    public String path;

    @NotBlank
    public String value;
}
