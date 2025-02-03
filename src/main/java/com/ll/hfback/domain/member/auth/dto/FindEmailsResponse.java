package com.ll.hfback.domain.member.auth.dto;

import java.util.List;

public record FindEmailsResponse(
    List<EmailInfo> emailInfos
) {}
